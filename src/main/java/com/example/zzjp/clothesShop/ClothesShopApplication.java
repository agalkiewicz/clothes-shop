package com.example.zzjp.clothesShop;

import com.example.zzjp.clothesShop.model.*;
import com.example.zzjp.clothesShop.repository.*;
import com.example.zzjp.clothesShop.util.PropertiesValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class ClothesShopApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ClothesShopApplication.class, args);
	}

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DeliveryRepository deliveryRepository;

	@Autowired
	private DiscountRepository discountRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		Category category1 = new Category();
		category1.setName(PropertiesValues.CATEGORY_NAME_1);
		category1.setId(PropertiesValues.CATEGORY_ID_1);

		Category category2 = new Category();
		category2.setName(PropertiesValues.CATEGORY_NAME_2);
		category2.setId(PropertiesValues.CATEGORY_ID_2);

		categoryRepository.save(Arrays.asList(category1, category2));

		Item item1 = new Item();
		item1.setId(PropertiesValues.ITEM_ID_1);
		item1.setCategory(category1);
		item1.setName(PropertiesValues.ITEM_NAME_1);
		item1.setPrice(PropertiesValues.PRICE_1);
		item1.setSize(PropertiesValues.SIZE_1);
		item1.setColor(PropertiesValues.COLOR_1);
		item1.setAmount(PropertiesValues.AMOUNT_1);

		Item item2 = new Item();
		item2.setId(PropertiesValues.ITEM_ID_2);
		item2.setCategory(category1);
		item2.setName(PropertiesValues.ITEM_NAME_2);
		item2.setPrice(PropertiesValues.PRICE_2);
		item2.setSize(PropertiesValues.SIZE_1);
		item2.setColor(PropertiesValues.COLOR_1);
		item2.setAmount(PropertiesValues.AMOUNT_2);

		Item item3 = new Item();
		item3.setId(PropertiesValues.ITEM_ID_3);
		item3.setCategory(category2);
		item3.setName(PropertiesValues.ITEM_NAME_3);
		item3.setPrice(PropertiesValues.PRICE_2);
		item3.setSize(PropertiesValues.SIZE_2);
		item3.setColor(PropertiesValues.COLOR_2);
		item3.setAmount(PropertiesValues.AMOUNT_3);

//		ItemState itemState1 = new ItemState();
//		itemState1.setAmount(1);
//
//		ItemState itemState2 = new ItemState();
//		itemState2.setAmount(2);
//
//		ItemState itemState3 = new ItemState();
//		itemState3.setAmount(0);

//		itemStateRepository.save(Arrays.asList(itemState1, itemState2, itemState3));

//		item1.setItemState(itemState1);
//		item2.setItemState(itemState2);
//		item3.setItemState(itemState3);

		itemRepository.save(Arrays.asList(item1, item2, item3));

		User user1 = new User();
		user1.setId(PropertiesValues.USER_ID_1);
		user1.setUsername(PropertiesValues.USERNAME_1);
		String password = passwordEncoder().encode(PropertiesValues.PASSSWORD_1);
		user1.setPassword(password);
		user1.addRole(PropertiesValues.ROLE_1);

		User user2 = new User();
		user2.setId(PropertiesValues.USER_ID_2);
		user2.setUsername(PropertiesValues.USERNAME_2);
		password = passwordEncoder().encode(PropertiesValues.PASSSWORD_2);
		user2.setPassword(password);
		user2.addRole(PropertiesValues.ROLE_2);

		User user3 = new User();
		user3.setId(PropertiesValues.USER_ID_3);
		user3.setUsername(PropertiesValues.USERNAME_3);
		password = passwordEncoder().encode(PropertiesValues.PASSSWORD_3);
		user3.setPassword(password);
		user3.addRole(PropertiesValues.ROLE_3);

		userRepository.save(Arrays.asList(user1, user2, user3));

		userRepository.save(Arrays.asList(user1, user2, user3));

		Order order1 = new Order(user1);
		order1.addItem(item1);
		order1.addItem(item2);

		Delivery delivery1 = new Delivery();
		delivery1.setName(PropertiesValues.DELIVERY_NAME_1);
		delivery1.setPrice(PropertiesValues.DELIVERY_PRICE_1);
		deliveryRepository.save(delivery1);

		Discount discount1 = new Discount();
		discount1.setDiscountType(DiscountType.SALE);
		discount1.setItem(item1);
		discount1.setPercentage(new BigDecimal("0.4"));

		Discount discount2 = new Discount();
		discount2.setDiscountType(DiscountType.DELIVERY);

		Discount discount3 = new Discount();
		discount3.setSumOfMoney(new BigDecimal("100.00"));
		discount3.setPercentage(new BigDecimal("0.2"));
		discount3.setDiscountType(DiscountType.MONEY);

		discountRepository.save(Arrays.asList(discount1, discount2, discount3));

		order1.setDelivery(delivery1);
		order1.setDiscount(discount1);
		order1.setUser(user2);
		orderRepository.save(order1);
	}
}
