package com.akobir.wsm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cs_users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToOne
    @JoinColumn(name = "attachment_id", unique = true)
    private Attachment attachment;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "main_photo")
    private String mainPhoto;

    public void setOrganization(Organization organization) {
        if (organization != null) {
            this.organization = organization;
        }
    }

    public void setWarehouse(Warehouse warehouse) {
        if (warehouse != null) {
            this.warehouse = warehouse;
        }
    }
}
