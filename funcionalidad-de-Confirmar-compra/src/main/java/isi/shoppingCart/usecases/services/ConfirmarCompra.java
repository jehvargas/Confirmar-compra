package isi.shoppingCart.usecases.services;

import isi.shoppingCart.entities.Cart;
import isi.shoppingCart.entities.CartItem;
import isi.shoppingCart.usecases.ports.CartRepository;
import java.util.List;

public class ConfirmarCompra {

    private CartRepository cartRepository;
    public ConfirmarCompra(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    public String execute() {
        Cart cart = cartRepository.getCart();
        List<CartItem> items = cart.getItems();

        if (items.isEmpty()) {
            return "El carrito no contiene articulos.";
        }
        StringBuilder recibo = new StringBuilder();
        recibo.append("===== RECIBO DE COMPRA =====\n\n");

        double total = 0.0;

        for (CartItem item : items) {
            String nombre = item.getProduct().getName();
            int cantidad = item.getQuantity();
            double subtotal = item.getSubtotal();

            recibo.append(nombre)
                    .append(" x").append(cantidad)
                    .append(" -> $ ").append(subtotal)
                    .append("\n");

            total += subtotal;
        }

        recibo.append("\nTOTAL: $ ").append(total);
        recibo.append("\n============================");

        cartRepository.save(new Cart());

        return recibo.toString();
    }
}