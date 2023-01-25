const btnMenu = document.querySelector("#btnMenu");
const menu = document.querySelector("#menu");
const subMenuBtn = document.querySelectorAll(".subMenu-btn");
const btnCerrarSesion = document.querySelector(".btnCerrarSesion");

btnMenu.addEventListener("click", ()=>{
  menu.classList.toggle("mostrar");
})

for(let i = 0; i < subMenuBtn.length; i++){
  subMenuBtn[i].addEventListener("click", function(){
    if(window.innerWidth < 750){
      const subMenu = this.nextElementSibling;
      const height = subMenu.scrollHeight;

      if(subMenu.classList.contains("desplegar")){
        subMenu.classList.remove("desplegar");
        subMenu.removeAttribute("style");
      }else{
        subMenu.classList.add("desplegar");
        subMenu.style.height = height + "px";
      }
    }
  })
};

btnCerrarSesion.addEventListener("click", ()=>{
  localStorage.clear();
  location.replace('/');
});

const JWT = localStorage.getItem('jwt') || location.replace('/');

let jwtParceNav = JSON.parse(atob(JWT.split('.')[1]));

if(jwtParceNav.role[0].authority === "ROLE_MANAGER"){
  const rolRestriction = document.querySelectorAll(".rolManager");
  rolRestriction.forEach(elemento =>{
    elemento.classList.remove("rolManager")
  })
}

if(jwtParceNav.role[0].authority === "ROLE_ADMIN"){
  const rolRestriction = document.querySelectorAll(".rolManager, .rolAdmin");
  rolRestriction.forEach(elemento =>{
    elemento.classList.remove("rolManager");
    elemento.classList.remove("rolAdmin");
  })
}
