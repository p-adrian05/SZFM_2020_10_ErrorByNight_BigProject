let postNumberAuthors = document.querySelectorAll(".postNumberAuthor");
let postAnswers = document.querySelectorAll(".answer");
document.addEventListener("DOMContentLoaded",()=>{
    let upperLimit = offset+range;
    let i = offset;
    let counts = new Array();
    while(i!==upperLimit){
        counts.push(i);
        i++;
    }
    i=0;
    let temp = 0;
    postNumberAuthors.forEach(postNumberAuthor =>{
        // if(authenticatedUsername !== null){
        //     if(postNumberAuthor.nextElementSibling.textContent.toString() === authenticatedUsername.toString()){
        //         postAnswers.item(i).style = "display:none";
        //     }
        // }
        temp = counts[i];
        postNumberAuthor.firstElementChild.textContent += temp;
        postNumberAuthor.setAttribute("href",postNumberAuthor.getAttribute("href").toString()+"?offset="+temp.toString()+"&range=1");
        i++;
    })
    i=0;
    postAnswers.forEach(answer =>{
        temp = counts[i];
        answer.setAttribute("href",answer.getAttribute("href").toString()+"&offset="+temp.toString());
        i++;
    })
})
