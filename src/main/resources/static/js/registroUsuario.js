const formulario = document.querySelector("#modificar");
const btnSubmit = document.querySelector("button[type=submit]");
const inputUsername = document.querySelector("input#username");
const inputPassword = document.querySelector("input#password");
const formRol = document.querySelector("#selectRol");

const errorNombre = document.querySelector("input#username ~ span");
const errorApellido = document.querySelector("input#password ~ span");
const url = "http://localhost:8080/auth/register";

formulario.addEventListener("submit", (event) => {
  event.preventDefault();
  if(validarInputs()){
    btnSubmit.setAttribute("disabled", "");
    
    const formData = {
      username: inputUsername.value,
      password: inputPassword.value,
      usuarioRol: formRol.value
    };

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
                  title: 'Usuario registrado!',
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

  if(inputUsername.value === ""){
    inputUsername.classList.add("inputError");
    errorNombre.style.display = "inline";
    correcto = false;
  }
  if(inputPassword.value == ""){
    inputPassword.classList.add("inputError");
    errorApellido.style.display = "inline";
    correcto = false;
  }
  return correcto;
};

function limipiarErrores (){
    document.querySelectorAll(".inputError").forEach(span=>span.classList.remove("inputError"))
    document.querySelectorAll(".incorrecto").forEach(input=>input.style.display="none");
};