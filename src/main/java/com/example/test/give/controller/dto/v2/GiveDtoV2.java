package com.example.test.give.controller.dto.v2;

import com.example.test.food.Food;
import com.example.test.give.Give;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GiveDtoV2 {
    private Long id;
    private LocalDateTime time;
    private String foodName;
    private String fridgeName;
    private String fridgeAddress;
    private Boolean isDone;

    public GiveDtoV2(Long id, LocalDateTime time, String foodName, String fridgeName, String fridgeAddress, boolean isDone) {
        this.id = id;
        this.time = time;
        this.foodName = foodName;
        this.fridgeName = fridgeName;
        this.fridgeAddress = fridgeAddress;
        this.isDone = isDone;
    }

    public GiveDtoV2 from(Give give) {
        return new GiveDtoV2(
                give.getId(),
                give.getGiveTime(),
                give.getFood().getName(),
                give.getFridge().getName(),
                give.getFridge().getAddress(),
                true
        );
    }

    public static List<GiveDtoV2> of(List<Give> gives){
        return gives.stream().map(
                give -> new GiveDtoV2(
                        give.getId(),
                        give.getGiveTime(),
                        give.getFood().getName(),
                        give.getFridge().getName(),
                        give.getFridge().getAddress(),
                        true
                )
        ).collect(Collectors.toList());
    }
}
