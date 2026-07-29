package com.cp.Lab5.controller;

import com.cp.Lab5.model.Coffee;
import com.cp.Lab5.service.CoffeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    private final CoffeeService coffeeService;

    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    // 1. GET ทั้งหมด
    @GetMapping
    public ResponseEntity<List<Coffee>> getAllCoffees() {
        return ResponseEntity.ok(coffeeService.getAllCoffees());
    }


    // 2. GET ตาม id 
    @GetMapping("/{id}")
    public ResponseEntity<Coffee> getCoffeeById(@PathVariable Long id) {
        return coffeeService.getCoffeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST เพิ่มเมนูใหม่
    @PostMapping
    public ResponseEntity<Coffee> addCoffee(@RequestBody Coffee coffee) {
        Coffee created = coffeeService.addCoffee(coffee);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 4. PUT แก้ไขตาม id
    @PutMapping("/{id}")
    public ResponseEntity<Coffee> updateCoffee(@PathVariable Long id, @RequestBody Coffee coffeeDetails) {
        return coffeeService.updateCoffee(id, coffeeDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE ลบตาม id 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoffee(@PathVariable Long id) {
        if (coffeeService.deleteCoffee(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}