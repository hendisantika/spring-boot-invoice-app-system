package com.hendisantika.repository;

import com.hendisantika.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
    @Query("select c from Client c left join fetch c.invoices i where c.id = ?1")
    Client fetchByIdWithInvoices(Long id);
}
