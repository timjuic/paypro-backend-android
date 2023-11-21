package air.found.payproandroidbackend.core;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer terminalId;

    @Column(length = 20)
    private String terminalKey;

    private Integer type;
    private Integer status;
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name = "merchantID", nullable = false)
    private Merchant merchant;

}
