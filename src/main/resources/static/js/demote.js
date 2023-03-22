const demoteLinks = document.querySelectorAll('#demote-link')

for (let demoteLink of demoteLinks) {
    demoteLink.addEventListener('click', function (event) {
        fetch(event.target.value, {method: 'PATCH'})
            .then(response => {

                if (response.status === 400) {
                    alert('Can\'t demote more than role User!')
                } else {
                    location.reload()
                }
            })
    })
}