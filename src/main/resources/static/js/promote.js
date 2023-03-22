const promoteLinks = document.querySelectorAll('#promote-link')

for (let promoteLink of promoteLinks) {
    promoteLink.addEventListener('click', function (event) {
        fetch(event.target.value, {method: 'PATCH'})
            .then(response => {

                if (response.status === 400) {
                    alert('Can\'t promote more than role Moderator!')
                } else {
                    location.reload()
                }
            })
    })
}


