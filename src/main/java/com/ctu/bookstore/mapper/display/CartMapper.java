//package com.ctu.bookstore.mapper.display;
//
//import com.ctu.bookstore.dto.respone.display.CartItemResponse;
//import com.ctu.bookstore.dto.respone.display.CartResponse;
//import com.ctu.bookstore.entity.display.Cart;
//import com.ctu.bookstore.entity.display.CartItem;
//import com.ctu.bookstore.entity.display.Product;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "spring", uses = {ProductMapper.class})
//public interface CartMapper {
//    @Mapping(target = "userId", source = "user.id")
//    @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "mapCartItems")
//    @Mapping(target = "totalAmount", expression = "java(calculateTotal(cart.getCartItems()))")
//    CartResponse toCartResponse(Cart cart);
//
//    @Mapping(target = "product", source = "product")
//    @Mapping(target = "subtotal", expression = "java(item.getPriceAtTime() * item.getQuantity())")
//    CartItemResponse toCartItemResponse(CartItem item);
//
//    // Helper method để ánh xạ Set<CartItem> sang List<CartItemResponse>
//    @Named("mapCartItems")
//    default List<CartItemResponse> mapCartItems(Set<CartItem> cartItems) {
//        return cartItems.stream()
//                .map(this::toCartItemResponse)
//                .collect(Collectors.toList());
//    }
//
//    // Helper method để tính tổng tiền
//    default Double calculateTotal(Set<CartItem> cartItems) {
//        double sumOfPrice = 0;
//        for(CartItem cartItem: cartItems){
//            for (Product product : cartItem.getProducts()){
//                 sumOfPrice = product.getSellingPrice()*product.getQuantity();
//            }
//        }
//        return sumOfPrice;
//
//    }
//
//}
