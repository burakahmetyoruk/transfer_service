package com.burak.domain.repository;

import com.burak.domain.entity.Transfer;
import org.springframework.data.repository.CrudRepository;

/**
 * Simple repository to allow CRUD-Methods for Transfer.
 * @author byoruk
 */
public interface TransferRepository extends CrudRepository<Transfer, Long> {
}