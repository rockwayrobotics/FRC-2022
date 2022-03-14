#!/usr/bin/env python3
'''Install dependencies needed for vision development.'''

import os
from pathlib import Path

ASSETS = Path(__file__).parent / 'assets'


if __name__ == '__main__':
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument('assets', nargs='*', default=[])
    args = parser.parse_args()

    import frc8089_utils as utils
    toml = utils._import('toml')

    prj = toml.load('pyproject.toml')
    pkgs = prj['project']['dependencies']

    # the requirements can include version constraints so ensure quotes around each
    reqs = ' '.join(f'"{x}"' for x in pkgs)
    # print(f'installing {reqs}')
    if os.system(f'pip install {reqs}') != 0:
        if os.system('pip -V'):
            print('Python pip is not installed.')

    # download/extract extra image assets
    for label in args.assets:
        import zipfile, requests
        dst = ASSETS
        dst.mkdir(parents=True, exist_ok=True)
        url = prj['tool']['frc8089']['assets'].get(label)
        if url:
            target = dst / (url.rsplit('/', 1)[1])
            if not target.exists():
                print('downloading', url)
                response = requests.get(url)
                if response.ok:
                    target.write_bytes(response.content)

            print('extracting', target)
            zf = zipfile.ZipFile(target)
            # extract to subfolder only if zip contents aren't already in one
            dest = dst if all('/' in x.filename for x in zf.infolist()) else target.with_suffix('')
            zf.extractall(path=dest)


# EOF
