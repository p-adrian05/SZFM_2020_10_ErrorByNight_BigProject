let messagesTimestamp = document.querySelectorAll(".timestamp");
document.addEventListener("DOMContentLoaded",()=>{
    findNewMessages(messagesTimestamp);
})
function findNewMessages(messagesTimestamp){
    let count = 0;
    newMessagesTimestamp.forEach((newMessagesTimestamp)=>{
        messagesTimestamp.forEach(messagesTimestamp =>{
                if(newMessagesTimestamp.toString()===messagesTimestamp.textContent.toString()){
                    if(count===0){
                        messagesTimestamp.setAttribute("id","scrollTo")
                    }
                    count++;
                    messagesTimestamp.parentElement.parentElement.parentElement.parentElement.lastElementChild.style.backgroundColor = "#66BB6A";
                    messagesTimestamp.parentElement.parentElement.parentElement.parentElement.lastElementChild.style.borderColor = "#f44336";
                }
            }
        )
    })
    document.getElementById("scrollTo").scrollIntoView();
}
