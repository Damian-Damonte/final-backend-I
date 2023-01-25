window.addEventListener('load',  () => {
  const formulario = document.querySelector("#formulario");
  const btnSubmit = document.querySelector("button[type=submit]");
  const inputUsername = document.querySelector("input#username");
  const inputPassword = document.querySelector("input#contrasenia");
  const errorUsername = document.querySelector("input#username ~ span");
  const errorPassword = document.querySelector("input#contrasenia ~ span");

  formulario.addEventListener("submit", (event) => {
    event.preventDefault();
    if(validarInputs()){
      mostrarSpinner();
      
      const formData = {
        username: inputUsername.value,
        password: inputPassword.value,
      }

      const url = "http://localhost:8080/auth/login";
      const settings = {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(formData)
      };

      fetch(url, settings)
      .then(response => response.status===200 ? response.json() : null)
      .then(data => {
        if(data!= null && data.jwt){
          localStorage.setItem('jwt', data.jwt);
          location.replace('/inicio.html');
        }
        else
          badCredentials();
        ocultarSpinner();
      })
    }
  });

  //VALIDACIONES

  function badCredentials(){
    limipiarErrores();
    inputUsername.classList.add("inputError");
    inputPassword.classList.add("inputError");
    errorPassword.textContent = "Credenciales incorrectas"
    errorPassword.style.display = "inline";
  }

  function validarInputs (){
    limipiarErrores();
    let correcto = true;

    if(inputUsername.value === ""){
      inputUsername.classList.add("inputError");
      errorUsername.style.display = "inline";
      correcto = false;
    }
    if(inputPassword.value == ""){
      inputPassword.classList.add("inputError");
    errorPassword.textContent = "Debe ingresar una contraseña"
      errorPassword.style.display = "inline";
      correcto = false;
    }
    return correcto;
  }

  function limipiarErrores (){
      document.querySelectorAll(".inputError").forEach(span=>span.classList.remove("inputError"))
      document.querySelectorAll(".incorrecto").forEach(input=>input.style.display="none");
  }


  function mostrarSpinner() {
    btnSubmit.setAttribute("disabled", "");
    const spinner = document.querySelector(".spinner");
    const ingresar = document.querySelector("#ingresar");

    spinner.classList.toggle("disabled");
    ingresar.classList.toggle("disabled");
  };

  function ocultarSpinner() {
    btnSubmit.removeAttribute("disabled");
    const spinner = document.querySelector(".spinner");
    const ingresar = document.querySelector("#ingresar");

    spinner.classList.toggle("disabled");
    ingresar.classList.toggle("disabled");
  };
});