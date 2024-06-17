package med.voll.api.model;


public record DatosRegistroPaciente(
        String nombre,
        String email,
        String telefono,
        String documentoIdentidad,
        DatosDireccion direccion
) {
}
