package nikos.tecniko.models;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;
import nikos.tecniko.enums.RepairType;
import nikos.tecniko.enums.StatusType;


@Setter
@Getter
@Entity
@ToString
public class PropertyRepair extends PersistentClass{

    @ManyToOne
    @JoinColumn(name="property_id")
    private Property property;
    private RepairType repairType;
    @Column(length = 128)
    private String description;
    private Instant dateOfSubmission;
    @Column(length = 512)
    private String workToBeDone;
    private Instant proposedStartDate;
    private Instant proposedEndDate;
    private BigDecimal cost;
    private boolean ownerAcceptance;
    private StatusType status;
    private Instant actualStartDate;
    private Instant actualEndDate;
}
