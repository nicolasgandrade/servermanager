package com.nicolasgandrade.servermanager.model;

import com.nicolasgandrade.servermanager.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    @NotEmpty(message = "IP address cannot be empty")
    private String ip;
    private String name;
    private String memory;
    private String type;
    private String imgUrl;
    private Status status;

}
