package by.academy.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MyTable(name = "car")
public class Car {
    @MyColumn(name = "id")
    private Integer id;
    @MyColumn(name = "name")
    private String name;
    @MyColumn(name = "colour")
    private String colour;
    @MyColumn(name = "price")
    private Integer price;

}
