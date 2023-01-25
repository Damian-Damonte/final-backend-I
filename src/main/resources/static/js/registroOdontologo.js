const formulario = document.querySelector("#formulario");
const btnSubmit = document.querySelector("button[type=submit]");
const inputNombre = document.querySelector("input#nombre");
const inputApellido = document.querySelector("input#apellido");
const inputMatricula = document.querySelector("input#matricula");
const errorNombre = document.querySelector("input#nombre ~ span");
const errorApellido = document.querySelector("input#apellido ~ span");
const errorMatricula = document.querySelector("input#matricula ~ span");

formulario.addEventListener("submit", (event) => {
  event.preventDefault();
  if(validarInputs()){
    btnSubmit.setAttribute("disabled", "");
    
    const formData = {
      nombre: inputNombre.value,
      apellido: inputApellido.value,
      matricula: inputMatricula.value
    };

    const url = "http://localhost:8080/odontologos";
    const settings = {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
        'Authorization': "Bearer " + JWT},
      body: JSON.stringify(formData)
    };

    fetch(url, settings)
    .then(response => {
      if(response.status === 201){
        Swal.fire({
          icon: 'success',
          title: 'Odontologo registrado!',
          toast: true,
          position: 'top',
          showConfirmButton: false,
          timer: 2000,
          timerProgressBar: true,
        })
      };
      formulario.reset();
    })
    .then(() => {
      btnSubmit.removeAttribute("disabled");
    })
  }
});

//VALIDACIONES

function validarInputs (){
  limipiarErrores();
  let correcto = true;

  if(inputNombre.value === ""){
    inputNombre.classList.add("inputError");
    errorNombre.style.display = "inline";
    correcto = false;
  };
  if(inputApellido.value == ""){
    inputApellido.classList.add("inputError");
    errorApellido.style.display = "inline";
    correcto = false;
  };
  if(inputMatricula.value == ""){
    inputMatricula.classList.add("inputError");
    errorMatricula.style.display = "inline";
    correcto = false;
  };
  return correcto;
};

function limipiarErrores (){
    document.querySelectorAll(".inputError").forEach(span=>span.classList.remove("inputError"))
    document.querySelectorAll(".incorrecto").forEach(input=>input.style.display="none");
};
