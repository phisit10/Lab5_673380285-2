package com.cp.Lab5.service;

import com.cp.Lab5.model.Coffee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private final List<Coffee> coffeeList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(3);

    public CoffeeService() {
        // เพิ่มข้อมูลตัวอย่าง 2 รายการเริ่มต้น
        coffeeList.add(new Coffee(1L, "Espresso", 45.0));
        coffeeList.add(new Coffee(2L, "Latte", 55.0));
    }

    public List<Coffee> getAllCoffees() {
        return coffeeList;
    }

    public Optional<Coffee> getCoffeeById(Long id) {
        return coffeeList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public Coffee addCoffee(Coffee coffee) {
        coffee.setId(idCounter.getAndIncrement());
        coffeeList.add(coffee);
        return coffee;
    }

    public Optional<Coffee> updateCoffee(Long id, Coffee coffeeDetails) {
        Optional<Coffee> existingCoffee = getCoffeeById(id);
        if (existingCoffee.isPresent()) {
            Coffee coffee = existingCoffee.get();
            coffee.setName(coffeeDetails.getName());
            coffee.setPrice(coffeeDetails.getPrice());
            return Optional.of(coffee);
        }
        return Optional.empty();
    }

    public boolean deleteCoffee(Long id) {
        return coffeeList.removeIf(c -> c.getId().equals(id));
    }

    // [Bonus] ฟีเจอร์ค้นหาตามชื่อ
    public List<Coffee> searchByName(String name) {
        return coffeeList.stream()
                .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}