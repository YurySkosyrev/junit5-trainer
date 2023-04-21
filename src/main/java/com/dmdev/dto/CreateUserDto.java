package com.dmdev.dto;

import lombok.Builder;
import lombok.Value;

/**
 * @Value – это неизменяемый вариант @Data
 *
 * @Builder - возможно, вам потребуется разработать объект-строитель,
 * который позволял бы вам создавать объекты, следуя пошаговой процедуре, например,
 * Author.builder().id("1").name("Maria").surname("Williams").build();.
 * Это особенно полезно, когда имеешь дело с большими классами,
 * в каждом из которых по несколько полей. Вместо использования конструктора
 * со многими полями, можно попробовать этот подход, более удобочитаемый.
 * При помощи аннотации @Builder вы поручаете Lombok генерировать строители  за вас.
 * Аннотируя класс при помощи @Builder, Lombok выдает класс,
 * реализующий вышеупомянутый паттерн «строитель».
 * Например, аннотируя ею класс Author, получим автоматически сгенерированный
 * класс AuthorBuilder
 */

@Value
@Builder
public class CreateUserDto {
    String name;
    String birthday;
    String email;
    String password;
    String role;
    String gender;
}
