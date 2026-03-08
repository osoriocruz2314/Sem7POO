package com.kdelectronics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kdelectronics.conexion.ConexionDB;
import com.kdelectronics.model.Producto;

public class ProductoDAO {

    public boolean crearProducto(Producto producto) {
        String sql = "INSERT INTO productos VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getCodigoProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setDouble(4, producto.getPrecioBase());
            stmt.setDouble(5, producto.getPrecioVenta());
            stmt.setString(6, producto.getCategoria());
            stmt.setInt(7, producto.getCantidadDisponible());
            stmt.setBoolean(8, producto.isActivo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear producto: " + e.getMessage());
            return false;
        }
    }

    public Producto buscarProductoPorCodigo(String codigo) {
        String sql = "SELECT * FROM productos WHERE codigoProducto = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Producto(
                    rs.getString("codigoProducto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precioBase"),
                    rs.getDouble("precioVenta"),
                    rs.getString("categoria"),
                    rs.getInt("cantidadDisponible"),
                    rs.getBoolean("activo")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre=?, descripcion=?, precioBase=?, precioVenta=?, categoria=?, cantidadDisponible=?, activo=? WHERE codigoProducto=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecioBase());
            stmt.setDouble(4, producto.getPrecioVenta());
            stmt.setString(5, producto.getCategoria());
            stmt.setInt(6, producto.getCantidadDisponible());
            stmt.setBoolean(7, producto.isActivo());
            stmt.setString(8, producto.getCodigoProducto());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProducto(String codigo) {
        String sql = "UPDATE productos SET activo = false WHERE codigoProducto = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE activo = true";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(new Producto(
                    rs.getString("codigoProducto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precioBase"),
                    rs.getDouble("precioVenta"),
                    rs.getString("categoria"),
                    rs.getInt("cantidadDisponible"),
                    rs.getBoolean("activo")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return productos;
    }
}
