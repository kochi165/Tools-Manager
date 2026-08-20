'use strict';

const dialogs = {
  login: document.getElementById('log-dialog'),
  register: document.getElementById('regi-dialog')
};
const forms = {
  login: document.getElementById('log-form'),
  register: document.getElementById('regi-form')
};
const error_messages = {
  login: document.getElementById('log-error-message'),
  register: document.getElementById('regi-error-message')
};
const param = new URLSearchParams(window.location.search);

if (param.has('error')) {
  history.replaceState(null, '', window.location.pathname);
  error_messages['login'].textContent = '入力情報が不正です';
  dialogs['login'].showModal();
} else {
  error_messages['login'].textContent = '';
}

//ウィンドウポップアップ
document.querySelectorAll('.account-button').forEach((button) => {
  button.addEventListener('click', (e) => {
    dialogs[e.currentTarget.dataset.type].showModal();
  });
});

document.querySelectorAll('.exit').forEach((button) => {
  button.addEventListener('click', (e) => {
    const datatype = e.currentTarget.dataset.type;
    error_messages[datatype].textContent = '';
    forms[datatype].reset();
    dialogs[datatype].close();
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

const inputName = document.getElementById('regi-Name');

const inputPass = document.getElementById('regi-Pass');

//httpリクエスト処理
async function accountControl() {
  try {
    const response = await fetch(
      'http://localhost:8080/accountController/register',
      {
        method: 'POST',
        headers: {
          'Content-type': 'application/json'
        },
        body: JSON.stringify({
          username: inputName.value,
          password: inputPass.value
        })
      }
    );
  } catch (error) {
    console.error('error');
  }
}

//リスナー登録
document.querySelectorAll('.entry').forEach((enter) => {
  enter.addEventListener('click', (e) => {
    const datatype = e.currentTarget.dataset.type;

    if (document.getElementById('regi-Pass2').value !== inputPass.value) {
      error_messages[datatype].textContent = 'パスワードが異なっています';
      return;
    } else {
      error_messages[datatype].textContent = '';
    }
    accountControl();
  });
});
