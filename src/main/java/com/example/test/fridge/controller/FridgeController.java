package com.example.test.fridge.controller;

import com.example.test.config.generic.Result;
import com.example.test.fridge.Fridge;
import com.example.test.fridge.controller.dto.AllFridgeDto;
import com.example.test.fridge.controller.dto.FridgeDtoResponse;
import com.example.test.fridge.service.FridgeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fridge")
public class FridgeController {

    private final FridgeService fridgeService;
    @GetMapping("/saveFridge")
    public String saveFridge(){
        return fridgeService.saveFridge();
    }

    @GetMapping("/all")
    public Result all(){
        return new Result<>(fridgeService.all());
    }

    @GetMapping("/getAll")
    public Result getAll(){
        return new Result<>(fridgeService.getall());
    }

    @GetMapping("/getFridge")
    public Result getFridge(@RequestParam String id){
        System.out.println("============= getFridge" + id+ " =============");
        return new Result(fridgeService.getFridge(id));
    }
}
