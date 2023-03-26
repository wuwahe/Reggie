package com.hbjt.reggie.dto;

import com.hbjt.reggie.domain.Setmeal;
import com.hbjt.reggie.domain.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
