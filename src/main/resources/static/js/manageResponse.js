function manageResponse(response, data) {
    response.json().then(errors => {
        Object.keys(errors).forEach(key => {
            const errorElement = document.querySelector(`#${key}-error`);
            errorElement.textContent = errors[key];
            errorElement.style.display = 'block';
        });

        for (let dataKey in data) {

            if (!errors.hasOwnProperty(dataKey)) {
                document.querySelector(`#${dataKey}-error`).style.display = 'none';
            }
        }
    })
}