package com.hendisantika.view.json;

import com.hendisantika.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 10.44
 */
@Component("list.json")
public class ClientListJsonView extends MappingJackson2JsonView {

    @Override
    protected Object filterModel(Map<String, Object> model) {

        model.remove("title");
        model.remove("page");

        @SuppressWarnings("unchecked")
        Page<Client> clients = (Page<Client>) model.get("clients");

        model.remove("clients");
        model.put("clients", clients.getContent());

        return super.filterModel(model);
    }
}
