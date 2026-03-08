package com.kdelectronics.main;

import java.util.List;
import java.util.Scanner;

import com.kdelectronics.dao.ProductoDAO;
import com.kdelectronics.model.Producto;

public class Main {
    public static void main(String[] args) {
        ProductoDAO dao = new ProductoDAO();
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("-------------SISTEMA DE INVENTARIO KD-ELECTRONICS----------");
            System.out.println("1. Crear producto");
            System.out.println("2. Buscar producto por código");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto (lógico)");
            System.out.println("5. Listar productos");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Código: ");
                    String codigo = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Descripción: ");
                    String descripcion = sc.nextLine();
                    System.out.print("Precio base: ");
                    double precioBase = sc.nextDouble();
                    System.out.print("Precio venta: ");
                    double precioVenta = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Categoría: ");
                    String categoria = sc.nextLine();
                    System.out.print("Cantidad disponible: ");
                    int cantidad = sc.nextInt();
                    sc.nextLine();
                    Producto p = new Producto(codigo, nombre, descripcion, precioBase, precioVenta, categoria, cantidad, true);
                    if (dao.crearProducto(p)) {
                        System.out.println("Producto creado exitosamente.");
                    } else {
                        System.out.println("Error al crear producto.");
                    }
                    break;
                case 2:
                    System.out.print("Código: ");
                    String codBuscar = sc.nextLine();
                    Producto prod = dao.buscarProductoPorCodigo(codBuscar);
                    if (prod != null && prod.isActivo()) {
                        System.out.println(prod);
                    } else {
                        System.out.println("Producto no encontrado o inactivo.");
                    }
                    break;
                case 3:
                    System.out.print("Código: ");
                    String codActualizar = sc.nextLine();
                    Producto prodActualizar = dao.buscarProductoPorCodigo(codActualizar);
                    if (prodActualizar != null && prodActualizar.isActivo()) {
                        System.out.print("Nuevo nombre: ");
                        prodActualizar.setNombre(sc.nextLine());
                        System.out.print("Nueva descripción: ");
                        prodActualizar.setDescripcion(sc.nextLine());
                        System.out.print("Nuevo precio base: ");
                        prodActualizar.setPrecioBase(sc.nextDouble());
                        System.out.print("Nuevo precio venta: ");
                        prodActualizar.setPrecioVenta(sc.nextDouble());
                        sc.nextLine();
                        System.out.print("Nueva categoría: ");
                        prodActualizar.setCategoria(sc.nextLine());
                        System.out.print("Nueva cantidad disponible: ");
                        prodActualizar.setCantidadDisponible(sc.nextInt());
                        sc.nextLine();
                        prodActualizar.setActivo(true);
                        if (dao.actualizarProducto(prodActualizar)) {
                            System.out.println("Producto actualizado.");
                        } else {
                            System.out.println("Error al actualizar producto.");
                        }
                    } else {
                        System.out.println("Producto no encontrado o inactivo.");
                    }
                    break;
                case 4:
                    System.out.print("Código: ");
                    String codEliminar = sc.nextLine();
                    if (dao.eliminarProducto(codEliminar)) {
                        System.out.println("Producto eliminado (lógico).");
                    } else {
                        System.out.println("Error al eliminar producto.");
                    }
                    break;
                case 5:
                    List<Producto> productos = dao.listarProductos();
                    if (productos.isEmpty()) {
                        System.out.println("No hay productos activos.");
                    } else {
                        for (Producto prodList : productos) {
                            System.out.println(prodList);
                        }
                    }
                    break;
                case 6:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
            System.out.println();
        } while (opcion != 6);
    }
}
