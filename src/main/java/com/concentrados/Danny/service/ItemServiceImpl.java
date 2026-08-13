package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Item;
import com.concentrados.Danny.domain.Producto;
import com.concentrados.Danny.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpSession session;

    @Autowired
    private ProductoRepository productoRepository;

    @SuppressWarnings("unchecked")
    @Override
    public List<Item> getItems() {
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems == null) {
            listaItems = new ArrayList<>();
            session.setAttribute("listaItems", listaItems);
        }
        return listaItems;
    }

    @Override
    public Item getItem(Item item) {
        List<Item> lista = getItems();
        for (Item i : lista) {
            if (i.getIdProducto().equals(item.getIdProducto())) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void save(Item item) {
        List<Item> lista = getItems();
        boolean existe = false;

        for (Item i : lista) {
            if (i.getIdProducto().equals(item.getIdProducto())) {
                existe = true;
                if (i.getCantidad() < i.getStock()) {
                    i.setCantidad(i.getCantidad() + 1);
                }
                break;
            }
        }

        if (!existe) {
            Producto producto = productoRepository.findById(item.getIdProducto()).orElse(null);
            if (producto != null) {
                Item nuevoItem = new Item(producto);
                nuevoItem.setCantidad(1);
                lista.add(nuevoItem);
            }
        }
        session.setAttribute("listaItems", lista);
    }

    @Override
    public void delete(Item item) {
        List<Item> lista = getItems();
        lista.removeIf(i -> i.getIdProducto().equals(item.getIdProducto()));
        session.setAttribute("listaItems", lista);
    }

    @Override
    public void update(Item item) {
        List<Item> lista = getItems();
        for (Item i : lista) {
            if (i.getIdProducto().equals(item.getIdProducto())) {
                i.setCantidad(item.getCantidad());
                break;
            }
        }
        session.setAttribute("listaItems", lista);
    }

    @Override
    public void clear() {
        session.removeAttribute("listaItems");
    }

    @Override
    public double getTotal() {
        List<Item> lista = getItems();
        BigDecimal total = BigDecimal.ZERO;
        for (Item i : lista) {
            total = total.add(BigDecimal.valueOf(i.getSubTotal()));
        }
        return total.doubleValue();
    }

    @Override
    public void facturar() {
        // La lógica de rebajo de inventario y generación de venta se orquesta desde VentaController / Facturacion
        clear();
    }
}