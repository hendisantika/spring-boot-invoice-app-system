package com.hendisantika.repository;

import com.hendisantika.entity.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
    @Query("select i from Invoice i join fetch i.client c join fetch i.items l join fetch l.product where i.id = ?1")
    Invoice fetchByIdWithClientWithInvoiceItemWithProduct(Long id);
}
