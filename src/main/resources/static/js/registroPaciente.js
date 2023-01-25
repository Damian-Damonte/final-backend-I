const form = document.querySelector("form");
const formNombre = document.querySelector("input#nombre");
const formApellido = document.querySelector("input#apellido");
const formEmail = document.querySelector("input#email");
const formDni = document.querySelector("input#dni");
const formFechaIngreso = document.querySelector("input#fechaIngreso");
const formCalle = document.querySelector("input#calle");
const formNumero = document.querySelector("input#numero");
const formLocalidad = document.querySelector("input#localidad");
const formProvincia = document.querySelector("input#provincia");
const formSubmit = document.querySelector("form button");
const errorApellido = document.querySelector("input#apellido ~ span");
const errorNombre = document.querySelector("input#nombre ~ span");
const errorDni = document.querySelector("input#dni ~ span");
const errorFecha = document.querySelector("input#fechaIngreso ~ span");
const url = "http://localhost:8080/pacientes";

form.addEventListener("submit", (event) => {
  event.preventDefault();
  if(validarInputs()){
    formSubmit.setAttribute("disabled", "");
    
    const formData = {
      nombre: formNombre.value,
      apellido: formApellido.value,
      email: formEmail.value,
      dni: formDni.value,
      fechaIngreso: formFechaIngreso.value,
      domicilio:{
        calle: formCalle.value,
        numero: formNumero.value,
        localidad: formLocalidad.value,
        provincia: formProvincia.value
      }
    }

    const settings = {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + JWT 
      },
      body: JSON.stringify(formData)
    };

    fetch(url, settings)
    .then(response => {
      if(response.status === 201){
        Swal.fire({
                icon: 'success',
                title: 'Paciente registrado!',
                toast: true,
                position: 'top',
                showConfirmButton: false,
                timer: 2000,
                timerProgressBar: true,
              })
      };
      form.reset()
    })
    .then(() => formSubmit.removeAttribute("disabled"))
    .catch( () => formSubmit.removeAttribute("disabled"))
  }
});

  //VALIDACIONES

function validarInputs (){
  limipiarErrores();
  let correcto = true;

  if(formApellido.value === ""){
    formApellido.classList.add("inputError");
    errorApellido.style.display = "inline";
    correcto = false;
  };
  if(formNombre.value == ""){
    formNombre.classList.add("inputError");
    errorNombre.style.display = "inline";
    correcto = false;
  };
  if(formDni.value == ""){
    formDni.classList.add("inputError");
    errorDni.style.display = "inline";
    correcto = false;
  };
  if(formFechaIngreso.value == ""){
    formFechaIngreso.classList.add("inputError");
    errorFecha.style.display = "inline";
    correcto = false;
  };
  return correcto;
};

function limipiarErrores (){
    document.querySelectorAll(".inputError").forEach(span=>span.classList.remove("inputError"))
    document.querySelectorAll(".incorrecto").forEach(input=>input.style.display="none");
};
