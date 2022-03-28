package com.parking.repository.generic;

import com.parking.modelo.generic.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGenericRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
