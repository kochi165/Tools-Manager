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
