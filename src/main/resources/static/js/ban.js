const exampleModal = document.getElementById('exampleModal')

exampleModal.addEventListener('show.bs.modal', event => {
    // Button that triggered the modal
    const button = event.relatedTarget
    // Extract info from data-bs-* attributes
    const user = button.getAttribute('data-bs-whatever')
    // Update the modal's content.
    const modalTitle = exampleModal.querySelector('.modal-title')

    modalTitle.textContent = `Ban User: ${user}`

    document.getElementById('ban-confirm').addEventListener('click', () => {
        const data = {
            userUsername: user,
            reason: document.getElementById('reason').value.trim(),
            banTime: document.getElementById('time').value.trim()
        }

        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }

        fetch("http://localhost:8000/api/user-management/ban", requestOptions)
            .then(() => location.reload())
    })
})