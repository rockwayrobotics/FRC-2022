# For now, run via "py -m pytest" on Windows, not just "pytest"

from pathlib import Path

import pytest

import vision
from vision import green


@pytest.fixture
def sampledir(pytestconfig):
    p = pytestconfig.rootpath / '_assets/2022VisionSampleImages'
    if not p.exists():
        print('missing', p)
        raise SystemExit('run "bin/install-deps.py samples"')
    return p


# sanity test to verify the framework is even installed
def test_00():
    assert 1+1 == 2


# Note: this is NOT a good example test, or at least a good example of
# how to structure the code under test.  It's just a starting point.
def test_01(sampledir):
    im, res = green.filter(sampledir / 'Terminal8ft6in.png')
    assert res['count'] == 3


# EOF
