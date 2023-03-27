function displayAlertMessage() {
    const urlParams = new URLSearchParams(window.location.search);
    const alertMessage = urlParams.get('success');

    // Display the alert message if it's not empty
    if (alertMessage) {
        const successAlertPlaceholder = document.getElementById('liveSuccessAlertPlaceholder')

        const successAlert = (message) => {
            const wrapper = document.createElement('div')
            wrapper.innerHTML = [
                `<div class="alert alert-success alert-dismissible" role="alert">`,
                `   <div>${message}</div>`,
                '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
                '</div>'
            ].join('')

            successAlertPlaceholder.append(wrapper)
        }
        successAlert(alertMessage)
    }
}

// Call the function on page load
window.onload = displayAlertMessage;
