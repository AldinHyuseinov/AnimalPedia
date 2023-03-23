const banLinks = document.querySelectorAll('#ban-link')

for (let banLink of banLinks) {
    banLink.addEventListener('click', function (event) {
        let banReason = prompt("What's the reasoning?");

        const data = {
            userUsername: event.target.value,
            reason: banReason
        }

        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }

        fetch("http://localhost:8000/api/admin/ban", requestOptions)
            .then(() => location.reload())
    })
}