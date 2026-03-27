package app.v2.eurovisionproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "esc_show")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "show_date")
    private LocalDate date;

    @Column(name = "show_year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "show_type_id")
    private ShowType showType;
}
