package med.voll.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.DatosActualizarMedicoDTO;

@Table(name = "medicos")
@Entity(name = "medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String telefono;
    private String email;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;
    private boolean activo;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo = true;
        this.nombre = datosRegistroMedico.nombre();
        this.telefono = datosRegistroMedico.telefono();
        this.email = datosRegistroMedico.email();
        this.documento = datosRegistroMedico.documento();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
    }

    public void actualizaDatos(DatosActualizarMedicoDTO datosActualizarMedicoDTO) {

        if (datosActualizarMedicoDTO.nombre() != null) {
            this.nombre = datosActualizarMedicoDTO.nombre();
        }
        if (datosActualizarMedicoDTO.documento() != null) {
            this.documento = datosActualizarMedicoDTO.documento();
        }
        if (datosActualizarMedicoDTO.direccion() != null) {
            this.direccion = direccion.actualizaDatos(datosActualizarMedicoDTO.direccion());
        }

    }

    public void desactivarMedico() {
        this.activo = false;
    }
}
