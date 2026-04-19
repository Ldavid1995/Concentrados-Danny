package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Item;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpSession session; // Inyectamos la sesión para persistencia

    @Override
    public List<Item> gets() {
        // Obtenemos la lista directamente de la sesión
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems == null) {
            listaItems = new ArrayList<>();
            session.setAttribute("listaItems", listaItems);
        }
        return listaItems;
    }

    @Override
public void save(Item item) {
    boolean existe = false;
    List<Item> lista;

    // 1. Recuperamos el objeto de la sesión
    Object carritoSesion = session.getAttribute("carrito");

    // 2. Blindaje contra el ClassCastException
    if (carritoSesion instanceof List) {
        lista = (List<Item>) carritoSesion;
    } else {
        // Si es null o es un HashMap (el error), inicializamos una lista nueva
        lista = new ArrayList<>();
    }

    // 3. Lógica de agregar o incrementar cantidad
    for (Item i : lista) {
        if (i.getIdProducto().equals(item.getIdProducto())) {
            i.setCantidad(i.getCantidad() + item.getCantidad());
            existe = true;
            break;
        }
    }

    if (!existe) {
        lista.add(item);
    }

    // 4. Guardamos de vuelta la LISTA en la sesión
    session.setAttribute("carrito", lista);
}

    @Override
    public void delete(Item item) {
        List<Item> listaItems = gets();
        listaItems.removeIf(i -> i.getIdProducto().equals(item.getIdProducto()));
        session.setAttribute("listaItems", listaItems);
    }

    @Override
    public Item get(Item item) {
        List<Item> listaItems = gets();
        for (Item i : listaItems) {
            if (i.getIdProducto().equals(item.getIdProducto())) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void update(Item item) {
        List<Item> listaItems = gets();
        for (Item i : listaItems) {
            if (i.getIdProducto().equals(item.getIdProducto())) {
                i.setCantidad(item.getCantidad());
                break;
            }
        }
        session.setAttribute("listaItems", listaItems);
    }
}