package air.found.payproandroidbackend.core.models;

import air.found.payproandroidbackend.core.enums.StatusType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "statuses")
@Data
@NoArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Integer statusId;

    @Column(name = "status_name", length = 20)
    private String statusName;

    @OneToMany(mappedBy = "status")
    private List<Merchant> merchants;

    @OneToMany(mappedBy = "status")
    private List<Terminal> terminals;
}
