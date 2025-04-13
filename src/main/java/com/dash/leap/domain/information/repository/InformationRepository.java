package com.dash.leap.domain.information.repository;

import com.dash.leap.domain.information.entity.Information;
import com.dash.leap.domain.information.entity.enums.InfoType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long> {

    @Query("select i from Information i " +
            "where (:type is null or i.infoType = :type) " +
            "order by i.createdAt desc")
    Slice<Information> findByInfoType(@Param("type")InfoType infoType, Pageable pageable);
}
