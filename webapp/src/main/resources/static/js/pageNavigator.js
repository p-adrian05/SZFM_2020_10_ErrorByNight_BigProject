let dropDownLink = document.getElementById("dropdownLink");
let dropDownLinks = document.getElementById("dropdown-items");
let forwardButton = document.getElementById("forward");
let backButton = document.getElementById("back");
document.addEventListener("DOMContentLoaded",()=>{
    selectedDropdownOption();
    inactiveNavigateButton(forwardButton,dropDownLinks.lastElementChild);
    inactiveNavigateButton(backButton,dropDownLinks.lastElementChild);
})

function selectedDropdownOption(){

    dropDownLinks.childNodes.forEach((node)=>{
        console.log(node.textContent);
        console.log(node.parentElement);

        if(node.textContent.substr(0,node.textContent.indexOf("-"))===offset.toString()){
            dropDownLink.innerText = node.textContent;
            node.style.backgroundColor = "#455A64";
        }
    })
}
function inactiveNavigateButton(navigateButton,dropDownLinksElementChild){
    let offset = navigateButton.getAttribute("href").substr(navigateButton.getAttribute("href").indexOf("=")+1);
    let offsetAvailable = dropDownLinksElementChild.textContent.substr(0,dropDownLinksElementChild.textContent.indexOf("-"));
    if(parseInt(offset)>parseInt(offsetAvailable) || parseInt(offset)<1){
        navigateButton.setAttribute("href","javascript:void(0)");
        navigateButton.style.color = "#CFD8DC";
        navigateButton.style.cursor = "not-allowed";
    }
}