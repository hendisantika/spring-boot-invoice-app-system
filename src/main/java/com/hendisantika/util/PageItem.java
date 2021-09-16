package com.hendisantika.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 10.13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageItem {
    private int number;
    private boolean actual;
}
