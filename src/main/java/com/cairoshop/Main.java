package com.cairoshop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cairoshop.service.impl.BaseCommonServiceImpl;
import com.cairoshop.web.dtos.BaseDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;
import com.cairoshop.web.dtos.VendorInBriefDTO;
import com.cairoshop.web.dtos.VendorInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
//		List<String> fields = new ArrayList<>();
//		getFields(fields, ProductInDetailDTO.class);
//		System.out.println(fields.toString().replace("[", "").replace("]", ""));


//		System.out.println(ProductInDetailDTO.class.getCanonicalName());
	}

	public static <DDTO> Optional<DDTO> findById(int id, Class<DDTO> ddtoClass) {
//		List<Method> getters = Stream.of(ddtoClass.getMethods())
//			.filter(method -> method.getName().startsWith("get"))
//			.filter(method -> !method.getName().equals("getClass"))
//			.collect(Collectors.toList());




		return null;
	}

	static void getFields(List<String> fields, Class<?> current) {

		if (current.equals(Object.class)) return;

		fields.addAll(Stream.of(current.getDeclaredFields())
			.map(field -> field.getName())
			.collect(Collectors.toList()));

		getFields(fields, current.getSuperclass());
	}

}
