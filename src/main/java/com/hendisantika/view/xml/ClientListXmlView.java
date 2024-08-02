package com.hendisantika.view.xml;

import com.hendisantika.entity.Client;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app-system
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/09/21
 * Time: 10.18
 */
@Component("list.xml")
public class ClientListXmlView extends MarshallingView {

    @Autowired
    public ClientListXmlView(Jaxb2Marshaller marshaller) {
        super(marshaller);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        model.remove("title");
        model.remove("page");

        @SuppressWarnings("unchecked")
        Page<Client> clients = (Page<Client>) model.get("clients");

        model.remove("clients");

        model.put("clientList", new ClientList(clients.getContent()));

        super.renderMergedOutputModel(model, request, response);
    }
}
