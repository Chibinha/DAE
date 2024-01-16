package com.example.backend.ejbs;

import com.example.backend.entities.Package;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.glassfish.jaxb.runtime.v2.runtime.output.Pcdata;
import org.hibernate.Hibernate;

@Stateless
public class PackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public Package find(long id) {
        return entityManager.find(Package.class, id);
    }

    public Package findOrFail(long id) {
        Package aPackage = entityManager.getReference(Package.class, id);
        Hibernate.initialize(aPackage);
        return aPackage;
    }
}
