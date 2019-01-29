/* eslint-disable no-undef */
const assert = require('assert');

describe('Testing mocha - string manipulation', () => {
    describe('#length', () => {
        it('It should return 0 if it is an empty string', () => {
            assert.equal(''.length, 0);
        });
    });
});
