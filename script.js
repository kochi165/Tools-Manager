'use strict';

const dialogs = {
  login: document.getElementById('log-dialog'),
  register: document.getElementById('regi-dialog')
};

document.querySelectorAll('.account-button').forEach((button) => {
  button.addEventListener('click', (e) => {
    dialogs[e.currentTarget.dataset.type].showModal();
  });
});

document.querySelectorAll('.exit').forEach((button) => {
  button.addEventListener('click', (e) => {
    dialogs[e.currentTarget.dataset.type].close();
  });
});

document.querySelectorAll('dialog').forEach((dialog) => {
  dialog.addEventListener('click', (e) => {
    const rect = dialog.getBoundingClientRect();

    const isInDialog =
      rect.top <= e.clientY &&
      e.clientY <= rect.bottom &&
      rect.left <= e.clientX &&
      e.clientX <= rect.right;

    if (!isInDialog) {
      dialog.close();
    }
  });
});

const inputName = {
  login: document.getElementById('loginName'),
  register: document.getElementById('regi-Name')
};

const inputPass = {
  login: document.getElementById('loginPass'),
  register: document.getElementById('regi-Pass')
};

async function accountControl(e) {
  try {
    const datatype = e.currentTarget.dataset.type;
    const response = await fetch(
      `http://localhost:8080/accountController/${datatype}`,
      {
        method: 'POST',
        headers: {
          'Content-type': 'application/json'
        },
        body: JSON.stringify({
          username: inputName[datatype].value,
          password: inputPass[datatype].value
        })
      }
    );
  } catch (error) {
    console.error('error');
  }
}

document.querySelectorAll('.enter').forEach((enter) => {
  enter.addEventListener('click', (e) => {
    const datatype = e.currentTarget.dataset.type;
    let pass_error = document.getElementById('error-message');
    if (datatype === 'register') {
      if (
        document.getElementById('regi-Pass2').value !==
        inputPass[datatype].value
      ) {
        pass_error.textContent = 'パスワードが異なっています';
        return;
      } else {
        pass_error.textContent = '';
      }
    }
    accountControl(e);
  });
});
