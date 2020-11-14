package louis.server.entity;

import lombok.*;
import louis.server.entity.authbase.AbstractAuditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "redirect_uri")
public class RedirectUriEntity extends AbstractAuditable<Long> {

    @NonNull
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_details_id", nullable = false)
    private ClientDetailsEntity clientDetails;

    @NonNull
    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

}
