package com.springboot.coffee.controller;

import com.springboot.coffee.dto.CoffeePatchDto;
import com.springboot.coffee.dto.CoffeePostDto;
import com.springboot.coffee.dto.CoffeeResponseDto;
import com.springboot.coffee.entity.Coffee;
import com.springboot.coffee.mapper.CoffeeMapper;
import com.springboot.coffee.service.CoffeeService;
import com.springboot.response.MultiResponseDto;
import com.springboot.response.SingleResponseDto;
import com.springboot.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v11/coffees")
public class CoffeeController {
    private CoffeeMapper coffeeMapper;
    private CoffeeService coffeeService;
    private final static String COFFEE_DEFAULT_URL = "/v11/coffees";

    public CoffeeController(CoffeeMapper coffeeMapper, CoffeeService coffeeService) {
        this.coffeeMapper = coffeeMapper;
        this.coffeeService = coffeeService;
    }

    @PostMapping
    public ResponseEntity postCoffee(@RequestBody CoffeePostDto coffeePostDto) {
        Coffee coffee = coffeeService.createCoffee(coffeeMapper.coffeePostDtoToCoffee(coffeePostDto));
        URI location = UriCreator.createUri(COFFEE_DEFAULT_URL, coffee.getCoffeeId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{coffee-id}")
    public ResponseEntity patchCoffee(@Positive @PathVariable("coffee-id") long coffeeId,
                                      @RequestBody CoffeePatchDto coffeePatchDto) {
        coffeePatchDto.setCoffeeId(coffeeId);
        Coffee coffee = coffeeService.updateCoffee(coffeeMapper.coffeePatchDtoToCoffee(coffeePatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(coffeeMapper.coffeeToCoffeeResponseDto(coffee)), HttpStatus.OK);
    }

    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@Positive @PathVariable("coffee-id") long coffeeId) {
        Coffee coffee = coffeeService.findCoffee(coffeeId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(coffeeMapper.coffeeToCoffeeResponseDto(coffee)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCoffees(@RequestParam("page") int page,
                                     @RequestParam("size") int size) {
        Page<Coffee> coffeePage = coffeeService.findCoffees(page -1, size);
        List<Coffee> coffees = coffeePage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(coffeeMapper.coffeeToCoffeeResponseDtos(coffees), coffeePage),
                HttpStatus.OK);
    }

    @DeleteMapping("{coffee-id}")
    public ResponseEntity deleteCoffee(@PathVariable("coffee-id") long coffeeId) {
        coffeeService.deleteCoffee(coffeeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}