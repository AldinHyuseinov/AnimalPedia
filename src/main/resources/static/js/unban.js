const unbanLinks = document.querySelectorAll('#unban-link')

for (let unbanLink of unbanLinks) {
    unbanLink.addEventListener('click', function (event) {
        fetch(event.target.value, {method: 'DELETE'})
            .then(() => location.reload())
    })
}