package br.com.c137.project.financial.core.multitenancy.mastertenant.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
//Table columns already created before the project
@Entity
@Table(name = "cnaes", catalog = "financial_master")
@Data
@NoArgsConstructor
public class Cnae {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "secao")
    private String session;
    @Column(name = "divisao")
    private String division;
    @Column(name = "grupo")
    private String group;
    @Column(name = "classe")
    private String type;
    @Column(name = "sub_classe")
    private String subType;
    @Column(name = "denominacao")
    private String denomination;
}



